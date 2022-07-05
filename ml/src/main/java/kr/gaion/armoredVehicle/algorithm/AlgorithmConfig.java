package kr.gaion.armoredVehicle.algorithm;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
// 스프링 컨테이너는 @Configuration이 붙어있는 클래스를 자동으로 빈으로 등록해두고, 해당 클래스를 파싱해서 @Bean이 있는 메소드를 찾아서 빈을 생성해준다.
// @Configuration 안에서 @Bean을 사용해야 싱글톤을 보장받을 수 있으므로 @Bean 어노테이션은 반드시 @Configuration과 함께 사용해주어야 한다.
// 이러한 @Bean 어노테이션의 경우는 수동으로 빈을 직접 등록해줘야만 하는 상황인데, 주로 다음과 같을 때 사용한다.
// - 개발자가 직접 제어가 불가능한 라이브러리를 활용할 때
// - 애플리케이션 전범위적으로 사용되는 클래스를 등록할 때
// - 다형성을 활용하여 여러 구현체를 등록해주어야 할 때
// 예를 들어 우리가 객체를 Json 메세지로 변경하기 위해 Gson과 같은 외부 라이브러리를 사용한다고 하자.
// 그러면 해당 클래스를 싱글톤 빈으로 등록해주어야 1개의 객체만 생성하여 여러 클래스가 공유함으로써 메모리 상의 이점을 얻을 수 있을 것이다.
// 그런데 해당 클래스는 우리가 만든게 아니라 가져다 쓰는 클래스일 뿐이므로 불가피하게 @Bean으로 등록해줘야만 한다.
//  눈에 파악하여 유지보수하기 좋기 때문이다. 예를 들어 여러 구현체를 빈으로 등록해줄 때 어떠한 구현체들이 빈으로 등록되는지를 파악하려면 소스 코드를 찾아볼 필요 없이 해당 @Configuration 클래스만 보면 되기 때문이다.

@ConfigurationProperties("algorithm")
// ConfigurationProperties: *.properties , *.yml 파일에 있는 property를 자바 클래스에 값을 가져와서(바인딩) 사용할 수 있게 해주는 어노테이션
// Spring boot 에서는 운영에 필요한 설정(DB 정보, LOG설정 등등 )들을 *.properties , *.yml 에 써두고 관리한다.
// 이 설정은 KEY - VALUE 의 형태로 저장되어 관리하고 있으며 @Value 을 사용하여 바인딩을 할 수 있다.

@Getter
@Setter
public class AlgorithmConfig {
  private Integer maxResult;
}
