package umc.spring.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.spring.member.domain.Member;

public interface MemberRepository extends JpaRepository<Long, Member> {
}
