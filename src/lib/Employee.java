package lib;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;


public class Employee {

	private String employeeId;
    private String firstName;
    private String lastName;
    private String idNumber;
    private String address;
    private int yearJoined;
    private int monthJoined;
    private int dayJoined;
    private boolean isForeigner;
    private boolean gender;
    private int monthlySalary;
    private int otherMonthlyIncome;
    private int annualDeductible;
	private MonthlyIncome monthlyIncome;
    private String spouseName;
    private String spouseIdNumber;
    private List<String> childNames;
    private List<String> childIdNumbers;

    public Employee(EmployeeData data) {
        this.employeeId = data.getEmployeeId();
        this.firstName = data.getFirstName();
        this.lastName = data.getLastName();
        this.idNumber = data.getIdNumber();
        this.address = data.getAddress();
        this.yearJoined = data.getYearJoined();
        this.monthJoined = data.getMonthJoined();
        this.dayJoined = data.getDayJoined();
        this.isForeigner = data.isForeigner();
        this.gender = data.isGender();
        this.childNames = new LinkedList<String>();
        this.childIdNumbers = new LinkedList<String>();
    }
	
	/**
	 * Fungsi untuk menentukan gaji bulanan pegawai berdasarkan grade kepegawaiannya (grade 1: 3.000.000 per bulan, grade 2: 5.000.000 per bulan, grade 3: 7.000.000 per bulan)
	 * Jika pegawai adalah warga negara asing gaji bulanan diperbesar sebanyak 50%
	 */
	
	public void setMonthlySalary(int grade) {	
		if (grade == 1) {
			monthlyIncome = new MonthlyIncome(3000000, isForeigner);
		} else if (grade == 2) {
			monthlyIncome = new MonthlyIncome(5000000, isForeigner);
		} else if (grade == 3) {
			monthlyIncome = new MonthlyIncome(7000000, isForeigner);
		}
	}
	
	public void setAnnualDeductible(int deductible) {	
		this.annualDeductible = deductible;
	}
	
	public void setAdditionalIncome(int income) {	
		monthlyIncome.setOtherIncome(income);
	}
	
	public void setSpouse(String spouseName, String spouseIdNumber) {
		this.spouseName = spouseName;
		this.spouseIdNumber = idNumber;
	}
	
	public void addChild(String childName, String childIdNumber) {
		childNames.add(childName);
		childIdNumbers.add(childIdNumber);
	}
	
	public int getAnnualIncomeTax() {
		LocalDate date = LocalDate.now();
		int monthWorkingInYear;
		if (date.getYear() == yearJoined) {
			monthWorkingInYear = date.getMonthValue() - monthJoined;
		} else {
			monthWorkingInYear = 12;
		}
		return TaxFunction.calculateTax(monthlyIncome.getTotal(), monthlyIncome.getOtherIncome(), monthWorkingInYear, annualDeductible, spouseIdNumber.equals(""), childIdNumbers.size());
	}
}

class MonthlyIncome {
    private int salary;
    private int otherIncome;

    public MonthlyIncome(int salary, boolean isForeigner) {
        this.salary = isForeigner ? (int) (salary * 1.5) : salary;
    }

    public void setOtherIncome(int otherIncome) {
        this.otherIncome = otherIncome;
    }

    public int getTotal() {
        return salary + otherIncome;
    }
	public int getOtherIncome() {
		return otherIncome;
	}
}
