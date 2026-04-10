// RegisterMemberCommand.java
public class RegisterMemberCommand implements Command {
    private Member member;
    private GymDatabase db;

    public RegisterMemberCommand(Member member, GymDatabase db) {
        this.member = member;
        this.db = db;
    }

    @Override
    public void execute() {
        db.addMember(member); // Citations for execution logic
    }

    @Override
    public void undo() {
        db.removeMember(member); // Removes the specific member
        System.out.println("⟲ Undo: Registration for " + member.getName() + " has been reversed.");
    }
}