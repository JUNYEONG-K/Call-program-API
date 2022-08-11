package fis.police.fis_police_server.domain.enumType;

public enum HasCar {
    CAR, WALK;

    public static boolean converter(HasCar hasCar){
        return hasCar == HasCar.CAR;
    }
}
