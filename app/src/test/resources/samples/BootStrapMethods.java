class BootStrapMethods {

    public static void main(String[] args) {
        System.out.printf("%f\n",  calculateAgeInSeconds(30));
    }

    public static double calculateAgeInSeconds(int age) {
        double ageInDays = age * 365.25;
        System.out.println("ageInDays: " + ageInDays );
        double ageInHours = ageInDays * 24;
        System.out.println("ageInHours: " + ageInHours );
        double ageInMinutes = ageInHours * 60;
        System.out.println("ageInMinutes: " + ageInMinutes );
        double ageInSeconds = ageInMinutes * 60;
        return age * 365.25 * 24 * 60 * 60;
    }

}