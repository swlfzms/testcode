import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.Hashtable;

public class EmployeeCache {

	private static EmployeeCache employeeCache;
	private Hashtable<String, EmployeeRef> employeeRefHashTable;
	private ReferenceQueue<Employee> q;

	private class EmployeeRef extends SoftReference<Employee> {

		private String key = "";

		public EmployeeRef(Employee em, ReferenceQueue<Employee> q) {
			super(em, q);
			key = em.getId();
			// TODO Auto-generated constructor stub
		}
	}

	private EmployeeCache() {
		employeeRefHashTable = new Hashtable<String, EmployeeRef>();
		q = new ReferenceQueue<Employee>();
	}

	public static EmployeeCache getInstance() {
		if (employeeCache == null) {
			employeeCache = new EmployeeCache();
		}
		return employeeCache;
	}

	private void cacheEmployee(Employee em) {
		cleanCache();
		EmployeeRef ref = new EmployeeRef(em, q);
		employeeRefHashTable.put(em.getId(), ref);
	}

	private void clearCache() {
		EmployeeRef ref = null;
		while ((ref = (EmployeeRef) q.poll()) != null) {
			employeeRefHashTable.remove(ref.key);
		}
	}

	// 清除Cache内的全部内容
	public void cleanCache() {
		cleanCache();
		employeeRefHashTable.clear();
		System.gc();
		System.runFinalization();
	}

	// 依据所指定的ID号，重新获取相应Employee对象的实例
	public Employee getEmployee(String id) {
		Employee em = null;
		// 缓存中是否有该Employee实例的软引用，如果有，从软引用中取得。
		if (employeeRefHashTable.contains(id)) {
			EmployeeRef ref = (EmployeeRef) employeeRefHashTable.get(id);
			em = (Employee) ref.get();
		}
		// 如果没有软引用，或者从软引用中得到的实例是null，重新构建一个实例，
		// 并保存对这个新建实例的软引用
		if (em == null) {
			em = new Employee(id);
			System.out.println("Retrieve From EmployeeInfoCenter. ID=" + id);
			this.cacheEmployee(em);
		}
		return em;
	}
}
