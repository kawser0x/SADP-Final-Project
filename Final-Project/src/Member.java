public class Member {
    private final String id;
    private final String name;
    private final int age;
    private final String phone;
    private Membership activeMembership;

    private Member(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.age = builder.age;
        this.phone = builder.phone;
    }

    public String getId() { return id; }
    public String getName() { return name; }

    public void setActiveMembership(Membership membership) {
        this.activeMembership = membership;
    }

    public Membership getActiveMembership() {
        return activeMembership;
    }

    @Override
    public String toString() {
        String plan = (activeMembership == null) ? "None" : activeMembership.getPlanName();
        return String.format("ID: %-5s | Name: %-15s | Age: %-3d | Plan: %-15s", id, name, age, plan);
    }

    public static class Builder {
        private String id;
        private String name;
        private int age;
        private String phone;

        public Builder(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public Builder setAge(int age) {
            this.age = age;
            return this;
        }

        public Builder setPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public Member build() {
            return new Member(this);
        }
    }
}