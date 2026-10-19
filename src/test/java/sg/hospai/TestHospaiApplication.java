package sg.hospai;

import org.springframework.boot.SpringApplication;

public class TestHospaiApplication {

	public static void main(String[] args) {
		SpringApplication.from(HospaiApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
