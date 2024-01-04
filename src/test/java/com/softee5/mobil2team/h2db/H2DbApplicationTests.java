package com.softee5.mobil2team.h2db;

import com.softee5.mobil2team.entity.Member;
import com.softee5.mobil2team.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class H2DbApplicationTests {

    private final MemberRepository memberRepository;

    @Autowired
    public H2DbApplicationTests(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Test
    void 회원가입() {
        Member member = new Member();
        member.setName("수현");
        memberRepository.save(member);

        Member findMember = memberRepository.findById(member.getId()).get();
        Assertions.assertThat(member.getName()).isEqualTo(findMember.getName());
    }
}
