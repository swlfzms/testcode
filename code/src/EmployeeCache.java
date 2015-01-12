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

	// ���Cache�ڵ�ȫ������
	public void cleanCache() {
		cleanCache();
		employeeRefHashTable.clear();
		System.gc();
		System.runFinalization();
	}

	// ������ָ����ID�ţ����»�ȡ��ӦEmployee�����ʵ��
	public Employee getEmployee(String id) {
		Employee em = null;
		// �������Ƿ��и�Employeeʵ���������ã�����У�����������ȡ�á�
		if (employeeRefHashTable.contains(id)) {
			EmployeeRef ref = (EmployeeRef) employeeRefHashTable.get(id);
			em = (Employee) ref.get();
		}
		// ���û�������ã����ߴ��������еõ���ʵ����null�����¹���һ��ʵ����
		// �����������½�ʵ����������
		if (em == null) {
			em = new Employee(id);
			System.out.println("Retrieve From EmployeeInfoCenter. ID=" + id);
			this.cacheEmployee(em);
		}
		return em;
	}
}
