package models;

public class TestData {

    private String url;
    private String endpoint;
    private String product;
    private Credentials credentials;

    public String getUrl() {
        return url;
    }

    public String getEndpoint(){
        return endpoint;
    }

    public String getProduct() {
        return product;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public static class Credentials {
        private String email;
        private String name;
        private String password;

        public String getEmail(){
            return email;
        }

        public String getName() {
            return name;
        }

        public String getPassword() {
            return password;
        }
    }
}
