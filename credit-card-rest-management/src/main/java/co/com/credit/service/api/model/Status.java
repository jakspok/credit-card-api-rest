package co.com.credit.service.api.model;

import io.swagger.annotations.ApiModel;
import java.io.Serializable;

@ApiModel(value ="Status")
public enum Status implements Serializable {
        ACTIVE(1),
        INACTIVE(2),
        BLOCKED(3);

        private int id;

        Status(int id) {
                this.id = id;
        }

        public int getId(){
                return id;
        }
}
