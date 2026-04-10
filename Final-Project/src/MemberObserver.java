
interface MemberObserver {
    void update(Member member);
}

class EmailNotification implements MemberObserver {
    @Override
    public void update(Member member) {
        System.out.println("[EMAIL] Sending welcome email to: " + member.getName());
    }
}

class SMSNotification implements MemberObserver {
    @Override
    public void update(Member member) {
        System.out.println("[SMS] Sending welcome text to: " + member.getName());
    }
}