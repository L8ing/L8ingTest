package ranker.dialog;

import java.util.Properties;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import ranker.View;
import ranker.util.Utils;

public class FilterRankDialog extends Dialog {

	private Text text;

	public String t = "";

	/**
	 * @param parentShell
	 * @param expireText
	 */
	public FilterRankDialog(Shell parentShell) {
		super(parentShell);
		setBlockOnOpen(true);
	}

	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("filter rank");
	}

	protected Point getInitialSize() {
		return getShell().computeSize(250, 150);
	}

	protected int getShellStyle() {
		return SWT.CLOSE | SWT.TITLE | SWT.APPLICATION_MODAL;
	}

	protected Control createDialogArea(Composite parent) {
		Composite pane = (Composite) super.createDialogArea(parent);
		Composite composite = new Composite(pane, SWT.NONE);
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		composite.setLayout(new GridLayout(1, false));
		text = new Text(composite, SWT.BORDER);
		text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		Properties properties = Utils.loadPropertiesFromFile(Utils
				.getPropFile());
		String s = properties.getProperty(View.FILTERRANK);
		if (s != null) {
			text.setText(s);
		}
		return pane;
	}

	protected void okPressed() {
		t = text.getText();
		super.okPressed();
	}

}
