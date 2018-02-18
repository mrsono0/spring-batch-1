package spring.batch.demon;

/**
 * Order
 * RabbitMQ 메시지 연동 데이터 포맷
 *
 * @author Eddy.Kim
 */

public class Order {

    private String coffeePath;

    public String getCoffeePath() {
        return coffeePath;
    }

    public void setCoffeePath(String coffeePath) {
        this.coffeePath = coffeePath;
    }
}
