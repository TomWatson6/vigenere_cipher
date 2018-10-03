
public class VigenereCipherCoursework {

	//The password used to encrypt and decrypt given text
	public static final String PASSWORD = "ABC";
	public static final String FILE_PATH = "./Text_To_Encrypt.txt";
	public static final String ENCRYPTED_FILE_PATH = "./Encrypted_text.txt";
	public static final String DECRYPTED_TEXT_FILE = "./Decrypted_Text.txt";
	public static final String ANONYMOUSLY_ENCRYPTED_FILE_PATH = "./Anonymously_encrypted_text.txt";
	public static final String DECRYPTION_WITHOUT_PASSWORD = "./Decryption_Without_Password.txt";


	public static void main(String[] args) throws Exception {

		String password;

		VigenereCipher vc = new VigenereCipher(PASSWORD);

		vc.encrypt(FILE_PATH);

		vc.decrypt(ENCRYPTED_FILE_PATH, DECRYPTED_TEXT_FILE, PASSWORD);

		password = vc.findPassword(ANONYMOUSLY_ENCRYPTED_FILE_PATH);

		vc.decrypt(ANONYMOUSLY_ENCRYPTED_FILE_PATH, DECRYPTION_WITHOUT_PASSWORD, password);

		System.out.println("Done!");

	}

}