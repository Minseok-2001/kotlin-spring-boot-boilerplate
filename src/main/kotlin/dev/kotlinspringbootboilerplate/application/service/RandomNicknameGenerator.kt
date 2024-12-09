package dev.kotlinspringbootboilerplate.application.service

import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class RandomNicknameGenerator {
	companion object {
		private val adjectives = arrayOf(
			"귀여운", "발랄한", "사랑스러운", "똑똑한", "활발한", "졸린", "장난꾸러기", "용감한", "상냥한", "행복한",
			"느긋한", "온화한", "믿음직한", "애교 많은", "웃음 가득한", "엉뚱한", "당당한", "재빠른", "고요한", "부드러운"
		)

		private val animals = arrayOf(
			"강아지", "고양이", "햄스터", "토끼", "앵무새", "거북이", "고슴도치", "말티즈", "푸들", "치와와",
			"골든리트리버", "페르시안", "스피츠", "요크셔테리어", "러시안블루", "코뉴어", "페럿", "다람쥐", "이구아나", "비글"
		)
	}

	fun generateNickname(): String {
		val adjective = adjectives.random()
		val animal = animals.random()
		val number = Random.nextInt(1000)
		return "$adjective $animal $number"
	}
}
