package idusw.jw202112030.pims.pims.controller;

import idusw.jw202112030.pims.pims.model.Member;
import idusw.jw202112030.pims.pims.repository.MemberDAO;
import idusw.jw202112030.pims.pims.repository.MemberDAOImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "memberController",
        urlPatterns = {"/members/login-form.do", "/members/login.do", "/members/post-form.do",
                "/members/post.do", "/members/logout.do", "/members/detail.do", "/members/update.do", "/members/list.do", "/members/delete.do"})
public class MemberController extends HttpServlet {

    private void doProcess(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        String uri = request.getRequestURI();
        String action = uri.substring(uri.lastIndexOf('/') + 1); // action = post, get-list 등

        MemberDAO dao = new MemberDAOImpl();
        Member member = null;

        if (action.equals("login-form.do")) {
            request.getRequestDispatcher("./member-login-form.jsp").forward(request, response);
        } else if(action.equals("login.do")){
            member = new Member();
            member.setEmail(request.getParameter("email"));
            member.setPw(request.getParameter("pw"));

            Member retMember = dao.read(member);

            if (retMember != null) {

                if (retMember.getAdmin() == 1) {
                    session.setAttribute("admin", retMember);
                    session.setAttribute("logined", retMember);
                } else {
                    session.setAttribute("logined", retMember);
                }

                request.getRequestDispatcher("../main/index.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("../status/error.jsp").forward(request, response);
            }

        } else if(action.equals("logout.do")) {
            session.invalidate(); // session 무효
            request.getRequestDispatcher("../main/index.jsp").forward(request, response);
        } else if(action.equals("post-form.do")) {
            request.getRequestDispatcher("./member-post-form.jsp").forward(request, response);
        } else if (action.equals("post.do")) {
            // Repository or DAO, Model or DTO, Business Logic(Service)
            member = new Member();
            member.setEmail(request.getParameter("email"));
            member.setPw(request.getParameter("pw"));
            member.setUsername(request.getParameter("username"));
            member.setPhone(request.getParameter("phone"));
            member.setAddress(request.getParameter("address"));

            if (dao.create(member) > 0) {
                request.getRequestDispatcher("../status/message.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("../status/error.jsp").forward(request, response);
            }
        } else if(action.equals("detail.do")) {
            String memberSeqStr = request.getParameter("id"); // (1) URL에서 id(seq) 값 가져오기

            if (memberSeqStr != null && !memberSeqStr.isEmpty()) {
                try {
                    int memberSeq = Integer.parseInt(memberSeqStr); // (2) DAO에 새로운 메소드를 추가하여 seq로 회원 정보를 조회하도록 수정
                    Member retMember = dao.readBySeq(memberSeq);

                    if (retMember != null) {
                        request.setAttribute("member", retMember);
                        request.getRequestDispatcher("../members/member-update-form.jsp").forward(request, response);
                    } else {
                        request.getRequestDispatcher("../status/error.jsp").forward(request, response);
                    }
                } catch (NumberFormatException e) {
                    // id가 숫자가 아닌 경우
                    request.getRequestDispatcher("../status/error.jsp").forward(request, response);
                }
            } else {
                // id 파라미터가 없는 경우, 현재 로그인된 유저의 정보를 보여주는 기존 로직 유지
                Member loginedMember = (Member) session.getAttribute("logined");
                if (loginedMember != null) {
                    request.setAttribute("member", loginedMember);
                    request.getRequestDispatcher("../members/member-update-form.jsp").forward(request, response);
                } else {
                    request.getRequestDispatcher("../status/error.jsp").forward(request, response);
                }
            }
        } else if (action.equals("update.do")) {
                    member = (Member) session.getAttribute("logined");
                    if (member == null) {
                        request.getRequestDispatcher("../status/error.jsp").forward(request, response);
                        return;
                    }

                    member.setEmail(request.getParameter("email"));
                    member.setPw(request.getParameter("pw"));
                    member.setUsername(request.getParameter("username"));
                    member.setPhone(request.getParameter("phone"));
                    member.setAddress(request.getParameter("address"));

            if (dao.update(member) > 0) {
                session.setAttribute("logined", member);
                request.setAttribute("message", "회원 정보가 수정되었습니다");
                request.getRequestDispatcher("../status/message.jsp").forward(request, response);
            } else {
                request.setAttribute("message", "회원 정보 수정이 실패되었습니다");
                request.getRequestDispatcher("../status/error.jsp").forward(request, response);
            }

        } else if(action.equals("list.do")) {
            List<Member> memberList = dao.readList();
            if (memberList != null) {
                request.setAttribute("memberList", memberList); // 회원 목록을 request 객체에 추가
                request.getRequestDispatcher("../members/member-list-view.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("../status/error.jsp").forward(request, response);
            }
        } else if(action.equals("delete.do")) {
            String memberSeq = request.getParameter("id");

            Member memberToDelete = new Member();
            memberToDelete.setSeq(Integer.parseInt(memberSeq));

            dao.delete(memberToDelete);
            response.sendRedirect("../members/list.do?pn=1");
        }

    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        doProcess(request, response);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        doProcess(request, response);
    }
}
