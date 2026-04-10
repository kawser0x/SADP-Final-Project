interface Membership {
    String getPlanName();
    double getPrice();
    int getDurationInDays();
}

class MonthlyPlan implements Membership {
    @Override public String getPlanName() { return "Monthly Basic"; }
    @Override public double getPrice() { return 50.0; }
    @Override public int getDurationInDays() { return 30; }
}

class VIPPlan implements Membership {
    @Override public String getPlanName() { return "VIP Platinum"; }
    @Override public double getPrice() { return 120.0; }
    @Override public int getDurationInDays() { return 90; }
}

public class MembershipFactory {
    public static Membership getMembership(int type) {
        switch (type) {
            case 1: return new MonthlyPlan();
            case 2: return new VIPPlan();
            default: return null;
        }
    }
}