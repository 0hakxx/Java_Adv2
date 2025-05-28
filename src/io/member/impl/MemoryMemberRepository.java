package io.member.impl;

import io.member.Member;
import io.member.MemberRepository;

import java.util.ArrayList;
import java.util.List;
//가장 큰 단점으로 프로그램 종료 시 메모리에 회원이 저장되므로 모든 정보가 삭제됨.
public class MemoryMemberRepository implements MemberRepository {

    private final List<Member> members = new ArrayList<>();

    @Override
    public void add(Member member) {
        members.add(member);
    }

    @Override
    public List<Member> findAll() {
        return members;
    }
}
