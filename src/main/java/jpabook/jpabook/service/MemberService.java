package jpabook.jpabook.service;



import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpabook.domain.Member;
import jpabook.jpabook.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
//Transactional readOnly = true 로 하면  좀더 빠름 // 읽기 전용은 해주고 변경해야하는 곳은 따로 트랜 젝션을 걸어 줄것 기본값은  false
	
	private final  MemberRepository memberRepository;
	
	
	//회원 가입
	@Transactional
	public Long join(Member member) {
		// 중복 체크
		validateDuplicateMember(member);
		
		memberRepository.save(member);
		return member.getId();
	}


	private void validateDuplicateMember(Member member) {
		List<Member> findMembers= memberRepository.findByName(member.getName());
		if(!findMembers.isEmpty()) {
			throw new IllegalStateException("이미 존재하는 회원입니다.");
		}
	}
	
	//회원 전체 조회 
	public List<Member> findmebers(){
		return memberRepository.findAll();
	}
	
	// 회원 한건 조회
	public Member findOne(Long memberId) {
		return memberRepository.findOne(memberId);
	}

	@Transactional
	public void update(Long id, String name) {
		Member member =memberRepository.findOne(id);
		member.setName(name);
	}
	
}
