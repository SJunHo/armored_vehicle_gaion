package kr.gaion.railroad2.algorithm;

import kr.gaion.railroad2.algorithm.dto.input.BaseAlgorithmPredictInput;
import kr.gaion.railroad2.algorithm.dto.response.AlgorithmResponse;
import kr.gaion.railroad2.algorithm.dto.input.BaseAlgorithmTrainInput;

public interface IMLAlgorithm<I extends BaseAlgorithmTrainInput, I2 extends BaseAlgorithmPredictInput> {
  AlgorithmResponse train(I input) throws Exception;
  AlgorithmResponse predict(I2 input) throws Exception;
}

// 인터페이스: 건물의 기본 설계도. 인터페이스도 추상클래스처럼 다른 클래스를 작성하는데 도움을 주는 목적으로 작성하고 클래스와 다르게 다중상속(구현)이 가능합니다.
// 추상클래스는 IS - A "~이다". 인터페이스는 HAS - A "~을 할 수 있는".
// 모든 클래스가 인터페이스만을 상속받아 구현한다면, 모두 공통인 기능도 매번 다시 구현해야해서 번거로움.
// 그렇다고 추상 클래스만을 상속받아 구현하자니 자바는 한개 클래스만 상속이 가능해서 여러 기능을 여러 추상 클래스로 나누어 놓고 다 가져올 수 없음.
// 예를 들어 일반클래스 A, B, C가 있고 A, B, C가 상속받고 있는 추상클래스 AA, BB, CC가 있고 AA, BB, CC가 상속받고 있는 추상 클래스 X가 있다면 (X -> AA, BB, CC -> A, B, C)
// 추상 클래스 AA, BB, CC에 어떤 공통된 기능이 필요하고 일반클래스 A, B, C에도 또 다른 어떤 공통 기능이 필요하다면 각각 인터페이스에 만들어 놓고 서로 상속 받아서 쓴다.
// 왜? 클래스는 1개 밖에 상속 받지 못하지만, 인터페이스는 다중 상속이 가능하니까

// 추상클래스와 인터페이는 자식이 상속받아 가면 부모에게 있는 기능을 사용할 수 있게 해주는 것은 같다.
// 추상클래스는 이 추상 클래스를 상속받아 여기에 있는 기능을 이용하고, 자식이 추가로 자식만의 기능을 확장 할 수 있다.
// 인터페이스는 인터페이스에 구현된 기능만을 이용할 수 있고, 자식만의 기능을 추가할 수 없다.

