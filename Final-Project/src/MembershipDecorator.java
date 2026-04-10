public abstract class MembershipDecorator implements Membership {
    protected Membership decoratedMembership;

    public MembershipDecorator(Membership membership) {
        this.decoratedMembership = membership;
    }

    @Override
    public String getPlanName() {
        return decoratedMembership.getPlanName();
    }

    @Override
    public double getPrice() {
        return decoratedMembership.getPrice();
    }

    @Override
    public int getDurationInDays() {
        return decoratedMembership.getDurationInDays();
    }
}

class PersonalTrainerDecorator extends MembershipDecorator {
    public PersonalTrainerDecorator(Membership membership) {
        super(membership);
    }

    @Override
    public String getPlanName() {
        return super.getPlanName() + " + Personal Trainer";
    }

    @Override
    public double getPrice() {
        return super.getPrice() + 30.0; // Adds $30 to the base price
    }
}

class LockerDecorator extends MembershipDecorator {
    public LockerDecorator(Membership membership) {
        super(membership);
    }

    @Override
    public String getPlanName() {
        return super.getPlanName() + " + Locker Access";
    }

    @Override
    public double getPrice() {
        return super.getPrice() + 10.0; // Adds $10 to the base price
    }
}