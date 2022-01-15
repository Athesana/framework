package com.kh.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 * Annotation
 * 	- JDK5부터 추가된 기능으로 자바 코드에 추가적인 정보를 제공하는 메타데이터이다.
 * 	- 비즈니스 로직에 영향을 주지는 않지만, 컴파일 과정에서 유효성 체크, 코드를 어떻게 컴파일하고 처리할지 알려주는 정보를 제공한다.
 * 	- 어노테이션을 클래스, 메소드, 변수, 매개변수 등에 추가할 수 있다.
 */

// @Target : 어노테이션을 적용할 위치(대상)를 지정한다.
@Target({ElementType.METHOD, ElementType.TYPE})
// @Retention : 어노테이션의 유효범위(어느 시점까지 어노테이션이 영향을 미치는지 결정)를 지정할 때 사용한다.

// RetentionPolicy.RUNTIME : 컴파일 이후에도 JVM에 의해서 참조가 가능하다.
// RetentionPolicy.CLASS : 컴파일러 클래스를 참조할 때까지 유효하다. (실제 실행시에는 JVM에서 참조할 수 없다.)
// RetentionPolicy.SOURCE : 코드상에서만 유효하다.(컴파일하면 사라짐)
@Retention(RetentionPolicy.RUNTIME)
// @Inherited : 부모 클래스에서 어노테이션을 선언하면 자식 클래스에도 상속된다.
//@Inherited
public @interface NoLogging {

}
