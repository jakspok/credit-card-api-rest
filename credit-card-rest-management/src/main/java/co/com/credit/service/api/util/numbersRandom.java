package co.com.credit.service.api.util;

import io.swagger.annotations.ApiModel;
import lombok.*;

import javax.persistence.Entity;
import java.util.Random;

@ApiModel
public final class numbersRandom {

    public static String generateRandom(int length) {
        int min = (int) Math.pow(10, length - 1);
        int max = (int) Math.pow(10, length);

        Random random = new Random();

        return Integer.toString(random.nextInt(max - min) + min);
    }

}
