package test.android;

import com.example.test.android.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class TestActivity extends Activity implements OnClickListener {

	EditText nameText;

	EditText pwdText;

	EditText text3;

	Button button1;

	Button button2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);

		nameText = (EditText) findViewById(R.id.editText1);
		pwdText = (EditText) findViewById(R.id.editText2);
		text3 = (EditText) findViewById(R.id.editText3);
		button1 = (Button) findViewById(R.id.button1);
		button2 = (Button) findViewById(R.id.button2);
		button1.setOnClickListener(this);
		button2.setOnClickListener(this);
	}

	@Override
	public void onClick(View e) {
		// TODO Auto-generated method stub
		Button btn = (Button) e;
		if (btn == button1) {
			String name = nameText.getText().toString();
			String pwd = pwdText.getText().toString();
			String s = name + " : " + pwd;
			text3.setText(s);
		}

	}

}
