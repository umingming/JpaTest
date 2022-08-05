package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    /*
        MemberForm을 쓴 이유는
        화면에 딱 적합한 데이터를 받기 위해서임.
        엔티티 클래스가 지저분해지는 것을 방지함.
     */
    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result) { //BindingResult 오류 발생 시, 담겨서 코드가 실행됨.
        if (result.hasErrors()) {
            return "members/createMemberForm";
        }

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());
        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/"; //첫 번째 페이지로 넘어감.
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> memberList = memberService.findMemberList();
        model.addAttribute("members", memberList);
        return "members/memberList";
    }
}
