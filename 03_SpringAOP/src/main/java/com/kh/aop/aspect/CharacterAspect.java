package com.kh.aop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect // 일반적인 자바 클래스가 아니라 Aspect임을 나타내는 @어노테이션
public class CharacterAspect {
	// 포인트 커트를 한 번만 정의하고 필요할 때마다 참조할 수 있는 @Pointcut 어노테이션을 제공한다.
	@Pointcut("execution(* com.kh.aop.character.Character.quest(..)) && args(questName)")
	public void questPointCut(String questName) {	
	}
	
//	@Before("execution(* com.kh.aop.character.Character.quest(..))")
	public void beforeQuest(JoinPoint jp) {
		
		System.out.println(jp.getSignature().getName());
		System.out.println(jp.getSignature().getDeclaringTypeName());
		
		System.out.println(jp.getArgs()[0] + "퀘스트 준비 중..");
	}
	
//	@After("execution(* com.kh.aop.character.Character.quest(..)) && args(questName)")
	public void afterQuest(String questName) {
//		퀘스트 수행 결과에 상관없이 수행 후에 필요한 부가 작업을 수행한다.
		System.out.println(questName  + "퀘스트 수행 완료!");
	}
	
//	@AfterReturning(
//			value = "questPointCut(questName)",
//			returning = "result"
//	)
	public void questSuccess(String questName, String result) {
//		result : 결과를 담을 변수
//		퀘스트 수행이 완료된 후에 필요한 부가 작업을 수행한다.
		System.out.println(result);
		System.out.println(questName  + "퀘스트 수행 완료!");
	}
	
//	@AfterThrowing(
//			value = "questPointCut(questName)",
//			throwing = "exception"
//	)
	public void questFail(String questName, Exception exception) {
//		퀘스트 수행 중에 예외를 던졌을 때 필요한 부가 작업을 수행한다.
		System.out.println(exception.getMessage());
		System.out.println("에러가 발생했습니다. 다시 시도하세요.");
	}
		
	/*
	 * ProceedingJoinPoint 객체는 파라미터로 필수 값이다.
	 * jp 인터페이스의 proceed() 메소드를 호출하고, 이 메소드 전후를 기점으로 수행할 기능이 나뉜다.
	 * 
	 * 메소드에 리턴값이 있으면 proceed()에서 리턴값을 받아줘야 한다. 
	 * 이 때, Object를 매개값을 가지기 때문에 타입으로 형변환 시켜야 한다.
	 * 
	 * 매개 값 이동 경로 : test -> characterAspect -> chararcter -> result?
	 */
	@Around("execution(* com.kh.aop.character.Character.quest(..))")
	public String questAdvice(ProceedingJoinPoint jp) {
		String result = null;
		String questName = "[" + (String)jp.getArgs()[0] + "]";
		// ▲ 타겟 객체의 메소드에 새로 넘겨주기
		
//		퀘스트 수행되기 전 후로 처리해야 할 내용을 한 번에 수행한다.
		try {
			// before Advice에 대한 기능 수행
			System.out.println("퀘스트 준비 중..");
			
			// proceed()메소드를 통해서 타겟 객체(캐릭터)의 메소드(quest)를 실행시킨다.
//			jp.proceed();
		
			// 타겟 객체의 메소드에 리턴값이 있을 경우
//			result = (String)jp.proceed();
			
			// 타겟 객체의 메소드에 파라미터 값을 변경해서 전달해줄 경우
			result = (String)jp.proceed(new Object[] { questName }); 
			
			// after-returning Advice에 대한 기능 수행
			System.out.println("퀘스트 수행 완료!");
			
		} catch (Throwable e) {
			// after-throwing 어드바이스에 대한 기능을 수행
			System.out.println(e.getMessage());
			System.out.println("에러가 발생했습니다. 다시 시도하세요.");
		}
		
		return result;
		
	}

	
	/*
	 * 실습 문제
	 *  Sword, Bow의 attack 메소드 실행 시, @Around 어드바이스를 사용하여 코드를 작성하세요.
	 *  1. attack 메소드 정상 동작시, 
	 *  공격을 준비 중입니다.
	 *  "검을 휘두른다." or "민첩하게 활을 쏜다." 출력
	 *  공격을 성공했습니다.
	 *  2. attack 메소드 실행 중에 예외가 발생시,
	 *  공격을 준비 중입니다.
	 *  "에러가 발생하였습니다.."
	 */
	
	@Around("execution(* com.kh.aop.weapon.*.attack())")
	public String attackAdvice(ProceedingJoinPoint jp) {
		String result = null;
		
		try {
			System.out.println("공격을 준비 중입니다.");
			
			result = (String)jp.proceed();
			
			System.out.println(result);
			
			System.out.println("공격을 성공했습니다.");
		} catch (Throwable e) {
			
			System.out.println("에러가 발생하였습니다..");
		}
		
		return result;
	}
	
	
	
	
	
}
