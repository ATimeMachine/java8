package com.chen.java8.example.httpUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * 切面日志(此切面输出rest、provider所以方法的入参、出参、耗时)
 * 
 * @author hongyong
 * @date 2017年8月2日
 */
@Component
@Aspect
public class LogAspect {
	/**
	 * 每次打印日志的最大长度
	 */
	private static final int MAX_LOG_LENGTH = 1024 * 16;
	private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

	private ThreadLocal<String> requestMethod = new ThreadLocal<>();// 请求方法
	private ThreadLocal<String> requestParam = new ThreadLocal<>();// 请求参数
	private ThreadLocal<Object> outputParam = new ThreadLocal<>(); // 接口输出
	private ThreadLocal<Long> startTime = new ThreadLocal<>(); // 开始时间
	private ThreadLocal<Long> endTime = new ThreadLocal<>(); // 结束时间
	// private String clientIP;//客户端ip
	private List<String> whiteMethodList = new ArrayList<>();
	{
		whiteMethodList.add("getEnterpriseReleasedCouponList");
		whiteMethodList.add("getCouponCodes");
	}
	private static final int RANDOM_SEED = 100;
	private Random randomInstance = new Random(RANDOM_SEED);

	private static final int PRINT_FREQUENCY = 2;// 随机打印，数越大打印得越多

	@Pointcut("((execution(* com.chen.java8.example.httpUtils.*.*(..))) "
			+ " && !(execution(* com.chen.java8.example.httpUtils.HttpClientUtils.get(..)))) ")
	public void aspect() {

	}

	/**
	 * @Description 方法调用前触发 记录开始时间
	 * @param joinPoint
	 */
	@Before("aspect()")
	public void doBefore(JoinPoint joinPoint) {
		startTime.set(System.currentTimeMillis());// 记录方法开始执行的时间
	}

	/**
	 * @Description 方法调用后触发 记录结束时间
	 * @param joinPoint
	 */
	@After("aspect()")
	public void doAfter(JoinPoint joinPoint) {
		endTime.set(System.currentTimeMillis());// 记录方法执行完成的时间
		this.printOptLog();
		clearThreadField();
	}

	private void clearThreadField() {
		requestMethod.remove();
		requestParam.remove();
		outputParam.remove();
		startTime.remove();
		endTime.remove();
	}

	/**
	 * @Description 环绕触发
	 */
	@Around("aspect()")
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		requestMethod.set(pjp.getSignature().toString());
		Object[] paramsArray = pjp.getArgs();

		if (paramsArray != null && paramsArray.length > 0) {
			boolean containMutipart = false;
			for (Object param : paramsArray) {
				if (param != null && param.getClass().getName().indexOf("MultipartFormData") > 0) {
					containMutipart = true;
					break;
				}
			}
			if (!containMutipart) {
				requestParam.set(arrayToJsonString(paramsArray));
			}

		}

		// 执行真正的方法，此处不要try catch，否则afterThrow不会执行
		Object result = pjp.proceed();
		outputParam.set(result);
		return result;
	}

	// 配置抛出异常后通知,使用在方法aspect()上注册的切入点
	@AfterThrowing(pointcut = "aspect()", throwing = "ex")
	public void afterThrow(JoinPoint joinPoint, Exception ex) {
		logger.info("afterThrow " + joinPoint + "\t" + ex.getMessage());
	}

	/**
	 * 输出日志
	 */
	private void printOptLog() {
		try {
			Object result = outputParam.get();
			if (result == null || result.getClass().getName().contains("Response")) {
				logger.info("接口方法名:" + requestMethod.get() + "接口入参:" + requestParam.get());
				return;
			}
			String methodName = requestMethod.get();
			if (methodName == null)
				return;
			boolean isWhiteMethod = whiteMethodList.stream().filter(methodName::contains).count() > 0;
			if (isWhiteMethod) {
				printWhiteMethodLog();
			} else {
				printAllLog();
			}
		} catch (IOException e) {
			logger.error(requestMethod.get() + "接口出参转json出错：", e.getMessage());
		}
	}

	private void printWhiteMethodLog() throws IOException {
		boolean doLog = randomInstance.nextInt() % PRINT_FREQUENCY != 0;
		if (!doLog)
			return;// 指定列表内的方法采用其他的频率打印

		Object result = outputParam.get();
		StringBuilder outputBuilder = new StringBuilder();
		outputBuilder.append("接口方法名:").append(requestMethod.get()).append(";\n接口入参:");

		// 控制日志的长度,超过20KB就不打印了 builder
		String paramString = requestParam.get();
		if (paramString != null) {
			if (paramString.length() > MAX_LOG_LENGTH) {
				outputBuilder.append(paramString.substring(0, MAX_LOG_LENGTH));
			} else {
				outputBuilder.append(paramString);
			}
		}

		outputBuilder.append("\n接口出参:");
		String outputString = JSON.toJSONString(result);

		if (outputString.length() > MAX_LOG_LENGTH) {
			outputBuilder.append(outputString.substring(0, MAX_LOG_LENGTH));
		} else {
			outputBuilder.append(outputString);
		}

		if (endTime.get() != null && startTime.get() != null) {
			outputBuilder.append("\n接口耗时：").append(endTime.get() - startTime.get()).append("ms");
		}
		logger.info(outputBuilder.toString());
	}

	private void printAllLog() throws IOException {
		Object result = outputParam.get();
		StringBuilder outputBuilder = new StringBuilder();
		outputBuilder.append("接口方法名:").append(requestMethod.get()).append("; 接口入参:");

		String paramString = requestParam.get();
		if (paramString != null) {
			outputBuilder.append(paramString);
		}

		outputBuilder.append(" \n接口出参:");
		String outputString = JSON.toJSONString(result);
		outputBuilder.append(outputString);

		if (endTime.get() != null && startTime.get() != null) {
			outputBuilder.append("\n接口耗时：").append(endTime.get() - startTime.get()).append("ms");
		}
		logger.info(outputBuilder.toString());
	}

	/**
	 * 返回入参字符串
	 * 
	 * @param params
	 * @return
	 */
	private String arrayToJsonString(Object[] params) {
		List<Object> list = Arrays.asList(params);
		String json = null;
		try {
			json = JSON.toJSONString(list);
		} catch (Exception e) {
			logger.error(requestMethod + "接口入参转json出错：", e.getMessage());
		}
		return json;
	}
}