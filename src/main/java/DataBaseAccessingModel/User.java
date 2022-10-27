package DataBaseAccessingModel;

public class User {
	
	private int id;
	private String name;
	private String roll;
	private String address;
	
	
	//Parameterised Constructor
	
	public User(int id, String name, String roll, String address) {
		super();
		this.id = id;
		this.name = name;
		this.roll = roll;
		this.address = address;
	}
	
	//construction overrloaded;
	public User(String name, String roll, String address) {
		super();
		this.name = name;
		this.roll = roll;
		this.address = address;
	}
	
	//Getters and setters for accessing the private variable'
	
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getRoll() {
			return roll;
		}
		public void setRoll(String roll) {
			this.roll = roll;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
	

}
