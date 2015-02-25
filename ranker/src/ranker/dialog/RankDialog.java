/*
 * 特别声明：本技术材料受《中华人民共和国著作权法》、《计算机软件保护条例》等法律、法规、行政
 * 规章以及有关国际条约的保护，赞同科技享有知识产权、保留一切权利并视其为技术秘密。未经本公司书
 * 面许可，任何人不得擅自（包括但不限于：以非法的方式复制、传播、展示、镜像、上载、下载）使用，
 * 不得向第三方泄露、透露、披露。否则，本公司将依法追究侵权者的法律责任。特此声明！
 *
 * Special Declaration: These technical material reserved as the technical secrets by AGREE 
 * TECHNOLOGY have been protected by the "Copyright Law" "ordinances on Protection of Computer 
 * Software" and other relevant administrative regulations and international treaties. Without 
 * the written permission of the Company, no person may use (including but not limited to the 
 * illegal copy, distribute, display, image, upload, and download) and disclose the above 
 * technical documents to any third party. Otherwise, any infringer shall afford the legal 
 * liability to the company.
 */
package ranker.dialog;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * <DL>
 * <DT>概要说明</DT>
 * <p>
 * <DD>详细说明</DD>
 * </DL>
 * <p>
 * 
 * <DL>
 * <DT><B>使用范例</B></DT>
 * <p>
 * <DD>使用范例说明</DD>
 * </DL>
 * <p>
 * 
 * @author leiting
 * @author Agree Tech
 * @version
 */
public class RankDialog extends Dialog {

	private CCombo combo;

	public String rank = "";

	/**
	 * @param parentShell
	 * @param expireText
	 */
	public RankDialog(Shell parentShell) {
		super(parentShell);
		setBlockOnOpen(true);
	}

	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("rank");
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
		combo = new CCombo(composite, SWT.BORDER);
		combo.setItems(new String[] { "A", "B", "C" });
		combo.setEditable(false);
		return pane;
	}

	protected void okPressed() {
		int i = combo.getSelectionIndex();
		switch (i) {
		case 0:
			rank = "A";
			break;
		case 1:
			rank = "B";
			break;
		case 2:
			rank = "C";
			break;
		default:
			rank = "";
			break;
		}
		super.okPressed();
	}

}
