package cn.house.flink.hudi;

public class UserBean {
//id INT ,   NAME STRING,  age INT
    private int id;
    private String NAME;
    private int age;

    private String birthday;



    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "id=" + id +
                ", NAME='" + NAME + '\'' +
                ", age=" + age +
                '}';
    }
}


