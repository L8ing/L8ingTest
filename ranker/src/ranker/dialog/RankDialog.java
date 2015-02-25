/*
 * �ر������������������ܡ��л����񹲺͹�����Ȩ���������������������������ȷ��ɡ����桢����
 * �����Լ��йع�����Լ�ı�������ͬ�Ƽ�����֪ʶ��Ȩ������һ��Ȩ��������Ϊ�������ܡ�δ������˾��
 * ����ɣ��κ��˲������ԣ������������ڣ��ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����أ�ʹ�ã�
 * �����������й¶��͸¶����¶�����򣬱���˾������׷����Ȩ�ߵķ������Ρ��ش�������
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
 * <DT>��Ҫ˵��</DT>
 * <p>
 * <DD>��ϸ˵��</DD>
 * </DL>
 * <p>
 * 
 * <DL>
 * <DT><B>ʹ�÷���</B></DT>
 * <p>
 * <DD>ʹ�÷���˵��</DD>
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
