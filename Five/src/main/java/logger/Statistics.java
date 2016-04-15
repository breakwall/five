package logger;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeNode;

import model.Cell;

public class Statistics {
	private static Node treeNode = new Node(null, null, -1);
	private static Node current;
	private static Node root;

	public static void addRootNode() {
		root = new Node();
		current = root;
	}

	public static void rootNodeOver(Cell cell, int value) {
		root.move = cell;
		root.value = value;
		treeNode.children.add(root);
		root = null;
	}

	public static void tryMove(Cell child) {
		Node node = new Node(current, child, current.level + 1);
		current.children.add(node);
		current = node;
	}

	public static void rollbackMove(int value) {
		current.value = value;
		current = current.parent;
	}

	public static Node getTreeNode() {
		return treeNode;
	}

	public static void setSelectedChild(Cell minMaxCell) {
		for (Node node : current.children) {
			if (node.move == minMaxCell) {
				node.isSelected = true;
			}
		}
	}

	static class Node implements TreeNode {
		public boolean isSelected;
		Node parent;
		Cell move;
		int value;
		int level = 0;
		List<Node> children = new ArrayList<>();

		public Node() {
		}

		public Node(Node parent, Cell move, int level) {
			super();
			this.parent = parent;
			this.move = move;
			this.level = level;
		}

		@Override
		public TreeNode getChildAt(int childIndex) {
			if (level == -1) {
				return children.get(childIndex);
			} else {
				for(Node n : children) {
					if (n.isSelected) {
						return n;
					}
				}

				return children.get(childIndex);
			}
		}

		@Override
		public int getChildCount() {
			if (level == -1) {
				return children.size();
			}
			return 1;
		}

		@Override
		public TreeNode getParent() {
			return parent;
		}

		@Override
		public int getIndex(TreeNode node) {
			return children.indexOf(node);
		}

		@Override
		public boolean getAllowsChildren() {
			return true;
		}

		@Override
		public boolean isLeaf() {
			return children.isEmpty();
		}

		@Override
		public Enumeration<?> children() {
			Vector v = new Vector<>();
			TreeNode node = getChildAt(0);
			v.add(node);
			return v.elements();
		}

		@Override
		public String toString() {
			if (move == null) {
				return "root";
			}
			String s = level + ":" + move + " " + value;
			if (isSelected && level > 1) {
				Node root = getRoot();
				int i = treeNode.children.indexOf(root);
				if (i + level - 1 < treeNode.children.size()
						&& treeNode.children.get(i + level - 1).move == move) {
					s += " get";
				}
			}
			return s;
		}

		private Node getRoot() {
			if (parent == null) {
				return null;
			}
			root = parent;
			while (root.parent != null) {
				root = root.parent;
				if (root.level == 0) {
					break;
				}
			}

			return root;
		}
	}

	public static class NodeRenderer extends DefaultTreeCellRenderer
    {
        @Override
		public Component getTreeCellRendererComponent(JTree tree, Object value,
                                                      boolean selected, boolean expanded,
                                                      boolean leaf, int row, boolean hasFocus)
        {
            super.getTreeCellRendererComponent(tree, value,
                                               selected, expanded,
                                               leaf, row, hasFocus);
            Node node = (Node)value;
            if (node.isSelected) {
            	setForeground(Color.RED);
            }

            return this;
        }
    }
}
