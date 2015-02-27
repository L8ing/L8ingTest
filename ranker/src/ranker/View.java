package ranker;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.part.ViewPart;

import ranker.dialog.RankDialog;
import ranker.util.JsonUtil;
import ranker.util.Utils;

public class View extends ViewPart {
	private static final String REFRESH = "refresh";

	private static final String NAME = "name";

	private static final String RANK = "rank";

	private static final String FILE = "file";

	public static final String FILTER = "filter";

	public static final String FILTERRANK = "filterRank";

	private static final String VIEWER = "viewer";

	private static final int itemsCount = 4;

	public static final String ID = "ranker.view";

	public static final List<String> treePropList = Arrays.asList(new String[] {
			NAME, RANK });

	private Properties rankProperties;

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(Composite parent) {

		rankProperties = Utils.loadPropertiesFromFile(Utils.getRankFile());

		CTabFolder cFolder = new CTabFolder(parent, SWT.BORDER);

		for (int i = 0; i < itemsCount; i++) {
			CTabItem item1 = new CTabItem(cFolder, SWT.NONE);
			item1.setText("tab" + i);
			Composite composite1 = new Composite(cFolder, SWT.NONE);
			composite1.setLayoutData(new GridData(GridData.FILL_BOTH));
			composite1.setLayout(new GridLayout(1, false));
			item1.setControl(composite1);
			createPane(i, item1, composite1);
		}

		cFolder.setSelection(0);

		Properties properties = Utils.loadPropertiesFromFile(Utils
				.getPropFile());
		String s = properties.getProperty(FILE);
		String[] content;
		if (s == null) {
			content = new String[itemsCount];
			for (int i = 0; i < content.length; i++) {
				content[i] = "";
			}
		} else {
			content = JsonUtil.parse(s);
		}
		CTabItem[] items = cFolder.getItems();
		for (int i = 0; i < items.length; i++) {
			CTabItem item = items[i];
			String textFile = content[i];
			if (textFile != null && textFile.length() > 0) {
				TreeViewer viewer = (TreeViewer) item.getData(VIEWER);
				File parentDir = new File(textFile);
				viewer.setInput(parentDir);
				item.setText(textFile);
			}
		}
	}

	private void createPane(final int index, final CTabItem item1,
			final Composite parent) {
		Composite c = new Composite(parent, SWT.NONE);
		c.setLayout(new GridLayout(3, false));
		c.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		Button b = new Button(c, SWT.PUSH);
		b.setText("import");

		final Tree tree = new Tree(parent, SWT.BORDER | SWT.MULTI
				| SWT.H_SCROLL | SWT.V_SCROLL);
		tree.setLayoutData(new GridData(GridData.FILL_BOTH));
		tree.setHeaderVisible(true);
		tree.setLinesVisible(true);
		TreeColumn[] tc = tree.getColumns();
		for (int i = 0; i < tc.length; i++) {
			tc[i].dispose();
		}
		for (String s : treePropList) {
			TreeColumn col = new TreeColumn(tree, SWT.NONE);
			col.setText(s);
			col.setResizable(true);
			if (s.equals(RANK)) {
				col.setWidth(100);
			} else {
				col.setWidth(600);
			}

		}
		final TreeViewer viewer = new TreeViewer(tree);
		item1.setData(VIEWER, viewer);
		// viewer.setCellModifier(createTreeCellModifier(properties));
		viewer.setColumnProperties((String[]) treePropList
				.toArray(new String[treePropList.size()]));
		viewer.setContentProvider(new NameTreeContentProvider());
		viewer.setLabelProvider(new NameTreeLabelProvider());

		b.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				DirectoryDialog dialog = new DirectoryDialog(parent.getShell(),
						SWT.OPEN | SWT.MULTI);
				dialog.setText("ÇëÑ¡Ôñ¸ùÄ¿Â¼");
				File[] files = File.listRoots();
				if (files.length > 0) {
					dialog.setFilterPath(files[0].getAbsolutePath());
				}
				String textFile = dialog.open();
				if (textFile == null) {
					return;
				}

				File parentDir = new File(textFile);
				viewer.setInput(parentDir);
				item1.setText(textFile);

				Properties properties = Utils.loadPropertiesFromFile(Utils
						.getPropFile());

				String s = properties.getProperty(FILE);
				String[] content;
				if (s == null) {
					content = new String[itemsCount];
					for (int i = 0; i < content.length; i++) {
						content[i] = "";
					}
				} else {
					content = JsonUtil.parse(s);
				}
				content[index] = textFile;
				String prop = JsonUtil.build(content);
				properties.setProperty(FILE, prop);
				Utils.writeProperties2File(properties, Utils.getPropFile());
			}
		});

		final Menu menu = new Menu(parent.getShell(), SWT.POP_UP);
		tree.setMenu(menu);
		menu.addListener(SWT.Show, new Listener() {
			public void handleEvent(Event event) {
				MenuItem[] menuItems = menu.getItems();
				for (int i = 0; i < menuItems.length; i++) {
					menuItems[i].dispose();
				}
				final TreeItem[] items = tree.getSelection();
				if (items.length == 1) {
					String[] texts = { RANK, REFRESH };
					for (int i = 0; i < texts.length; i++) {
						MenuItem menuItem = new MenuItem(menu, SWT.PUSH);
						menuItem.setText(texts[i]);
						menuItem.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent e) {
								MenuItem item = (MenuItem) e.widget;
								String text = item.getText();
								if (text.equals(REFRESH)) {
									rankProperties = Utils
											.loadPropertiesFromFile(Utils
													.getRankFile());
									viewer.refresh();
								} else if (text.equals(RANK)) {
									handleRank(viewer, items[0]);
								}
							}
						});
					}
				}
			}
		});

		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				ISelection selection = event.getSelection();
				if (selection instanceof IStructuredSelection) {
					Object item = ((IStructuredSelection) selection)
							.getFirstElement();
					if (item == null) {
						return;
					}
					if (item instanceof File) {
						File file = (File) item;
						if (file.isFile()) {
							try {
								Runtime.getRuntime().exec(
										"cmd /c \"" + file.getAbsolutePath()
												+ "\"");
							} catch (IOException e) {
								e.printStackTrace();
							}
						} else if (file.isDirectory()) {
							if (viewer.getExpandedState(item)) {
								viewer.collapseToLevel(item, 1);
							} else {
								viewer.expandToLevel(item, 1);
							}
						}
					}
				}
			}
		});
	}

	private void handleRank(TreeViewer viewer, TreeItem treeItem) {
		RankDialog dialog = new RankDialog(viewer.getControl().getShell());
		int ret = dialog.open();
		if (ret == Window.OK) {
			File path = (File) treeItem.getData();
			String rank = dialog.rank;
			if (rank != null && rank.length() > 0) {
				File rankFile = Utils.getRankFile();
				Properties rankProperties = Utils
						.loadPropertiesFromFile(rankFile);
				rankProperties.setProperty(path.getAbsolutePath(), rank);
				Utils.writeProperties2File(rankProperties, rankFile);
			} else {
				MessageDialog.openError(viewer.getControl().getShell(),
						"error", "rank == null");
			}
		}
		viewer.refresh();
	}

	private class NameTreeContentProvider implements ITreeContentProvider {
		public Object[] getChildren(Object arg0) {
			if (arg0 instanceof File) {
				File l = (File) arg0;
				if (l.isDirectory()) {
					File[] children = l.listFiles(new FileFilter() {
						@Override
						public boolean accept(File pathname) {
							Properties properties = Utils
									.loadPropertiesFromFile(Utils.getPropFile());
							String s = properties.getProperty(FILTER);
							if (s != null) {
								String[] filter = JsonUtil.parse(s);
								for (String f : filter) {
									if (pathname.getName().endsWith(f)) {
										return false;
									}
								}
							}

							String rank = properties.getProperty(FILTERRANK);
							String[] ranks = JsonUtil.parse(rank);
							if (ranks.length == 0) {
								return true;
							}
							for (String f : ranks) {
								String r = Utils.getRank(pathname);
								if (f.equals(r)) {
									return true;
								}
							}
							return false;
						}
					});
					return children;
				} else {
					return null;
				}
			} else {
				return null;
			}
		}

		public Object getParent(Object arg0) {
			return ((File) arg0).getParentFile();
		}

		public boolean hasChildren(Object arg0) {
			Object[] obj = getChildren(arg0);
			return obj == null ? false : obj.length > 0;
		}

		public Object[] getElements(Object arg0) {
			return getChildren(arg0);
		}

		public void dispose() {

		}

		public void inputChanged(Viewer arg0, Object arg1, Object arg2) {

		}

	}

	private class NameTreeLabelProvider implements ITableLabelProvider {

		private Image fileImage;

		private Image dirImage;

		public NameTreeLabelProvider() {
			try {
				URL url = getClass().getResource("/icons/file.png");
				fileImage = ImageDescriptor.createFromURL(url).createImage();

				URL dirurl = getClass().getResource("/icons/dir.gif");
				dirImage = ImageDescriptor.createFromURL(dirurl).createImage();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}

		public void dispose() {
			fileImage.dispose();
			dirImage.dispose();
		}

		public boolean isLabelProperty(Object arg0, String arg1) {
			return false;
		}

		public void removeListener(ILabelProviderListener arg0) {
		}

		public void addListener(ILabelProviderListener listener) {
		}

		public Image getColumnImage(Object element, int columnIndex) {
			if (element instanceof File) {
				File l = (File) element;
				if (columnIndex == 0) {
					if (l.isDirectory()) {
						return dirImage;
					} else {
						return fileImage;
					}
				} else {
					return null;
				}
			} else {
				return null;
			}
		}

		public String getColumnText(Object element, int columnIndex) {
			if (columnIndex < 0 || columnIndex >= treePropList.size()) {
				return "";
			}
			File file = (File) element;
			if (columnIndex == 0) {
				return file.getName();
			} else if (columnIndex == 1) {
				return rankProperties.getProperty(file.getAbsolutePath());
			} else {
				return "";
			}
		}

	}

	public void setFocus() {
	}
}