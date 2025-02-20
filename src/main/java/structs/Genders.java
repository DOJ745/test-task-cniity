package structs;

public enum Genders
{
    MALE ("Male"),
    FEMALE ("Female"),
    OTHER ("Other");

    private String gender;

    Genders(String gender)
    {
        this.gender = gender;
    }

    public String getGender()
    {
        return gender;
    }

    @Override
    public String toString()
    {
        return "Gender{"
                + "gender='"
                + gender
                + '\''
                + '}';
    }
}