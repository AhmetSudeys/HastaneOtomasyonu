package Helper;

import java.awt.JobAttributes;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class Helper {
	
	
	public static void optionPaneChangeButtonText() {
		UIManager.put("OptionPane.cancelButtonText", "İptal");
		UIManager.put("OptionPane.noButtonText", "Hayır");
		UIManager.put("OptionPane.okButtonText", "Tamam");
		UIManager.put("OptionPane.yesButtonText", "Evet");


	}
	

	//bir mesaj gösterileceği zaman bu sınıfı kullanarak kolayca ve kendini tekrar etmeyerek kullanıcıya mesaj gönderiyorum
	public static void showMsg(String str) {
		String msg;
		optionPaneChangeButtonText();
		
		switch(str) {
		case "fill":
			msg = "Lütfen tüm alanları doldurunuz!";
			break;
			
		case "success":
			msg = "İşlem başarılı!";
			break;
			
			default:
				msg = str;		
		}
		
		JOptionPane.showMessageDialog(null, msg,"Mesaj",JOptionPane.INFORMATION_MESSAGE);
		
		//yukarıda bir metot tanımlayarak işimi kolaylaştırdım. Kullanıcıya mesaj yollayacağımda direkt bu metotu kullanacağım.
	}
	
	public static boolean confirm(String str) {
		String msg;
		optionPaneChangeButtonText();

		switch(str) {
		case "sure":
			msg = "Bu işlemi gerçekleştirmek istiyor musun?";
			break;
			
			default:
				msg = str;
				break;
		}
		int res = JOptionPane.showConfirmDialog(null, msg, "Dikkat!", JOptionPane.YES_NO_OPTION);
		if (res == 0) {
			return true;
		}else {
			return false;
		}
	}
	
	
}
