package apporio.com.vehicleapp.Setter_Getter_Files;

/**
 * Created by admin on 5/11/2016.
 */
public class RideEstimateResponse {


    /**
     * result : 1
     * Message : {"rate_card_id":"2","car_type":"9","first_2km":null,"after_2km":null,"wait_time_charges":null,"extra_charges":null,"minimum_rate":"10","per_mile_rate":"20","per_minute_rate":"15","pick_up_rate":"50","total_km":"40.752 Km","tot_pay":506.44221984}
     */

    private ResponseBean response;

    public ResponseBean getResponse() {
        return response;
    }

    public void setResponse(ResponseBean response) {
        this.response = response;
    }

    public static class ResponseBean {
        private int result;
        /**
         * rate_card_id : 2
         * car_type : 9
         * first_2km : null
         * after_2km : null
         * wait_time_charges : null
         * extra_charges : null
         * minimum_rate : 10
         * per_mile_rate : 20
         * per_minute_rate : 15
         * pick_up_rate : 50
         * total_km : 40.752 Km
         * tot_pay : 506.44221984
         */

        private MessageBean Message;

        public int getResult() {
            return result;
        }

        public void setResult(int result) {
            this.result = result;
        }

        public MessageBean getMessage() {
            return Message;
        }

        public void setMessage(MessageBean Message) {
            this.Message = Message;
        }

        public static class MessageBean {
            private String rate_card_id;
            private String car_type;
            private Object first_2km;
            private Object after_2km;
            private Object wait_time_charges;
            private Object extra_charges;
            private String minimum_rate;
            private String per_mile_rate;
            private String per_minute_rate;
            private String pick_up_rate;
            private String total_km;
            private double tot_pay;

            public String getRate_card_id() {
                return rate_card_id;
            }

            public void setRate_card_id(String rate_card_id) {
                this.rate_card_id = rate_card_id;
            }

            public String getCar_type() {
                return car_type;
            }

            public void setCar_type(String car_type) {
                this.car_type = car_type;
            }

            public Object getFirst_2km() {
                return first_2km;
            }

            public void setFirst_2km(Object first_2km) {
                this.first_2km = first_2km;
            }

            public Object getAfter_2km() {
                return after_2km;
            }

            public void setAfter_2km(Object after_2km) {
                this.after_2km = after_2km;
            }

            public Object getWait_time_charges() {
                return wait_time_charges;
            }

            public void setWait_time_charges(Object wait_time_charges) {
                this.wait_time_charges = wait_time_charges;
            }

            public Object getExtra_charges() {
                return extra_charges;
            }

            public void setExtra_charges(Object extra_charges) {
                this.extra_charges = extra_charges;
            }

            public String getMinimum_rate() {
                return minimum_rate;
            }

            public void setMinimum_rate(String minimum_rate) {
                this.minimum_rate = minimum_rate;
            }

            public String getPer_mile_rate() {
                return per_mile_rate;
            }

            public void setPer_mile_rate(String per_mile_rate) {
                this.per_mile_rate = per_mile_rate;
            }

            public String getPer_minute_rate() {
                return per_minute_rate;
            }

            public void setPer_minute_rate(String per_minute_rate) {
                this.per_minute_rate = per_minute_rate;
            }

            public String getPick_up_rate() {
                return pick_up_rate;
            }

            public void setPick_up_rate(String pick_up_rate) {
                this.pick_up_rate = pick_up_rate;
            }

            public String getTotal_km() {
                return total_km;
            }

            public void setTotal_km(String total_km) {
                this.total_km = total_km;
            }

            public double getTot_pay() {
                return tot_pay;
            }

            public void setTot_pay(double tot_pay) {
                this.tot_pay = tot_pay;
            }
        }
    }
}
