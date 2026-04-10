import java.util.ArrayList;
import java.util.List;

public class GymDatabase {
    private static GymDatabase instance;
    private List<Member> memberList;
    private List<MemberObserver> observers = new ArrayList<>();

    private GymDatabase() {
        memberList = new ArrayList<>();
    }

    public static GymDatabase getInstance() {
        if (instance == null) {
            instance = new GymDatabase();
        }
        return instance;
    }

    public void addObserver(MemberObserver observer) {
        observers.add(observer);
    }

    public void addMember(Member member) {
        memberList.add(member);
        notifyObservers(member); // Observer trigger
    }

    public void removeMember(Member member) {
        memberList.remove(member);
    }

    private void notifyObservers(Member member) {
        for (MemberObserver observer : observers) {
            observer.update(member);
        }
    }

    public List<Member> getMembers() {
        return memberList;
    }

    public void showStats() {
        System.out.println("\n--- DATABASE STATISTICS ---");
        System.out.println("Total Members Registered: " + memberList.size());
        long activeMembers = memberList.stream().filter(m -> m.getActiveMembership() != null).count();
        System.out.println("Active Memberships: " + activeMembers);
        System.out.println("---------------------------");
    }
}