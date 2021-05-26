package jpabook.jpabook.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import javax.persistence.EntityManager;

import jpabook.jpabook.domain.Member;
import jpabook.jpabook.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

	@Autowired MemberService memberService;
	@Autowired MemberRepository memberRepository;
	@Autowired EntityManager em;
	
	@Test
	//@Rollback(false) // 얘로 롤백을 안시킬수도 잇음  -- 기본값이  true 
	public void 회원가입() throws Exception{
		//given
		Member member = new Member();
		member.setName("jang");
		
		//when 
		Long saveId =memberService.join(member);
		
		// then
		em.flush();
		assertEquals(member, memberRepository.findOne(saveId));
	}
	
	@Test(expected = IllegalStateException.class) //IllegalStateException 이 에러가 발생해야 테스트가 정상 작동 
	public void 중복_회원_예외() throws Exception{
		//given
		Member member1 = new Member();
		member1.setName("jang");
		Member member2 = new Member();
		member2.setName("jang");
		//when 
		memberService.join(member1);
		memberService.join(member2);	// 예외가 발생해야한다 
	
		
		// then
		fail("에러가 발생했습니다.");
	}
}
