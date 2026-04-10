// GroupByPlanStrategy.java
import java.util.Iterator;
import java.util.List;

public class GroupByPlanStrategy implements MemberDisplayStrategy {
    @Override
    public void display(List<Member> members) {
        String[] plans = {"Monthly Basic", "VIP Platinum", "None"};
        System.out.println("\n--- PLAN-WISE REPORT ---");

        for (String plan : plans) {
            System.out.println("\n>>> " + plan);
            boolean found = false;
            Iterator<Member> it = members.iterator(); // Iterator Pattern
            while (it.hasNext()) {
                Member m = it.next();
                String memberPlan = (m.getActiveMembership() == null) ? "None" : m.getActiveMembership().getPlanName();
                if (memberPlan.equals(plan)) {
                    System.out.println("   - " + m.getName() + " (ID: " + m.getId() + ")");
                    found = true;
                }
            }
            if (!found) System.out.println("   (No members found)");
        }
    }
}