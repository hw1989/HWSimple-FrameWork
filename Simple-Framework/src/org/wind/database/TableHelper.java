package org.wind.database;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;
import org.wind.annotation.Table;

import android.database.sqlite.SQLiteDatabase;

public class TableHelper {
	private Class<?> clazz = null;
	// 抽象对象
	private DBInterface dbi = null;

	private TableHelper() {
	}

	/**
	 * 使用类进行初始化
	 * 
	 * @param clazz表对应的类
	 */
	public TableHelper(Class clazz) {
		if (!clazz.isAnnotationPresent(Table.class)) {
			throw new IllegalArgumentException("此类无注解,不能创建表!");
		}
		Field[] fields = clazz.getDeclaredFields();
		if (fields != null && fields.length > 0) {
			// 记录是否有注解的
			boolean flag = false;
			for (Field field : fields) {
				if (field.isAnnotationPresent(org.wind.annotation.Field.class)) {
					flag = true;
					break;
				}
			}
			if (!flag) {
				throw new IllegalArgumentException("没有属性使用注解,不能创建表!");
			}
		} else {
			throw new IllegalArgumentException("没有属性,不能创建表!");
		}
		this.clazz = clazz;
	}

	/**
	 * 使用类进行初始化
	 * 
	 * @param clazz表对应的类
	 */
	public TableHelper(DBInterface dbi) {
		if (dbi == null) {
			throw new IllegalArgumentException("未实现DBInterface接口!");
		}
		this.dbi = dbi;
	}

	public boolean creatTable(SQLiteDatabase db) {
		// StringBuilder sb = new StringBuilder();
		// if (clazz != null) {
		// Table table = clazz.getAnnotation(Table.class);
		// sb.append("create table if not exists ");
		// // 表名
		// sb.append(" ").append(table.DTname()).append(" (");
		// sb.append(" _id ").append(DataType.Type_Int)
		// .append(" primary key autoincrement ");
		// Field[] fields = clazz.getDeclaredFields();
		// for (Field field : fields) {
		// if (field.isAnnotationPresent(org.wind.annotation.Field.class)) {
		// sb.append(getColumn(field));
		// }
		// }
		// sb.deleteCharAt(sb.length() - 1);
		// sb.append(" )");
		// } else if (dbi != null) {
		// sb.append(dbi.getTableSQL());
		// }
		// db.execSQL(sb.toString());
		db.execSQL(getSQL());
		db.close();
		return true;
	}

	/**
	 * 获取sql语句
	 */
	public String getSQL() {
		StringBuilder sb = new StringBuilder();
		if (clazz != null) {
			Table table = clazz.getAnnotation(Table.class);
			sb.append("create table if not exists ");
			// 表名
			sb.append(" ").append(table.DTname()).append(" (");
			sb.append(" _id ").append(DataType.Type_Int)
					.append(" primary key autoincrement ");
			boolean flag = false;
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				if (field.isAnnotationPresent(org.wind.annotation.Field.class)) {
					if (!flag) {
						flag = true;
						sb.append(",");
					}
					sb.append(getColumn(field));
				}
			}
			sb.deleteCharAt(sb.length() - 1);
			sb.append(" )");
		} else if (dbi != null) {
			sb.append(dbi.getTableSQL());
		}
		return sb.toString();
	}

	/**
	 * 根据属性的数据类型，构造相应字段
	 * 
	 * @param field
	 *            对象的属性
	 */
	private StringBuilder getColumn(Field field) {
		StringBuilder sb = new StringBuilder();
		org.wind.annotation.Field inject = field
				.getAnnotation(org.wind.annotation.Field.class);
		if (inject.key() == true) {
			return sb;
		}
		sb.append(inject.name()).append(" ");
		if (Long.class.equals(field.getType())
				|| Long.TYPE.equals(field.getType())) {
			sb.append(DataType.Type_Int);
		} else if (Float.class.equals(field.getType())
				|| Float.TYPE.equals(field.getType())) {
			sb.append(DataType.TYPE_Real);
		} else if (Double.class.equals(field.getType())
				|| Double.TYPE.equals(field.getType())) {
			sb.append(DataType.TYPE_Real);
		} else if (Integer.class.equals(field.getType())
				|| Integer.TYPE.equals(field.getType())) {
			sb.append(DataType.Type_Int);
		} else if (Boolean.class.equals(field.getType())
				|| Boolean.TYPE.equals(field.getType())) {
			// sb.append(DataType.Type_Text); 使用int表示
			sb.append(DataType.Type_Int);
		} else if (Byte.class.equals(field.getType())
				|| Byte.TYPE.equals(field.getType())) {
			sb.append(DataType.Type_Int);
		} else {
			sb.append(DataType.Type_Text);
		}
		sb.append(" ,");
		return sb;
	}
}
