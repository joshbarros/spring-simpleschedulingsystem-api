package com.goldenglowitsolutions.simpleschedulingsystem.config;

import com.goldenglowitsolutions.simpleschedulingsystem.entity.Course;
import com.goldenglowitsolutions.simpleschedulingsystem.entity.Student;
import com.goldenglowitsolutions.simpleschedulingsystem.repository.CourseRepository;
import com.goldenglowitsolutions.simpleschedulingsystem.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Configuration class for initializing sample data.
 */
@Configuration
@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    private static final Random random = new Random();

    @Value("${app.load-sample-data:true}")
    private boolean loadSampleData;

    /**
     * Initializes sample data for the application.
     * This will only run in non-production environments.
     *
     * @param studentRepository the student repository
     * @param courseRepository the course repository
     * @return a CommandLineRunner to run at startup
     */
    @Bean
    @Profile("!prod")
    public CommandLineRunner initData(StudentRepository studentRepository, CourseRepository courseRepository) {
        return args -> {
            if (!loadSampleData) {
                logger.info("Sample data loading is disabled");
                return;
            }

            // Create and save courses (30+ courses)
            List<Course> courses = createCourses();
            courseRepository.saveAll(courses);
            
            // Create and save students (50 students)
            List<Student> students = createStudents();
            studentRepository.saveAll(students);
            
            // Assign random courses to students (3-7 courses per student)
            assignCoursesToStudents(students, courses, studentRepository);
            
            System.out.println("Enhanced sample data initialized with:");
            System.out.println(" - " + courses.size() + " courses");
            System.out.println(" - " + students.size() + " students");
            System.out.println(" - Multiple course-student assignments");
        };
    }
    
    /**
     * Creates a list of courses from different departments.
     *
     * @return list of courses
     */
    private List<Course> createCourses() {
        List<Course> courses = new ArrayList<>();
        
        // Computer Science Courses
        courses.add(new Course("CS101", "Introduction to Programming", "Fundamental concepts of programming using Java."));
        courses.add(new Course("CS201", "Data Structures", "Study of data structures and their applications."));
        courses.add(new Course("CS301", "Algorithms", "Design and analysis of algorithms."));
        courses.add(new Course("CS302", "Database Systems", "Introduction to database design and SQL."));
        courses.add(new Course("CS401", "Artificial Intelligence", "Introduction to AI concepts and algorithms."));
        courses.add(new Course("CS402", "Machine Learning", "Techniques for developing machine learning models."));
        courses.add(new Course("CS403", "Web Development", "Building dynamic web applications."));
        courses.add(new Course("CS404", "Mobile App Development", "Creating applications for iOS and Android."));
        courses.add(new Course("CS405", "Computer Networks", "Fundamentals of computer networking."));
        courses.add(new Course("CS406", "Cybersecurity", "Security principles and practices for software systems."));
        
        // Mathematics Courses
        courses.add(new Course("MATH101", "Calculus I", "Introduction to differential calculus."));
        courses.add(new Course("MATH102", "Calculus II", "Introduction to integral calculus."));
        courses.add(new Course("MATH201", "Linear Algebra", "Vector spaces, matrices, and linear transformations."));
        courses.add(new Course("MATH301", "Probability", "Introduction to probability theory."));
        courses.add(new Course("MATH302", "Statistics", "Statistical analysis and inference."));
        courses.add(new Course("MATH401", "Differential Equations", "Solving and applications of differential equations."));
        
        // Physics Courses
        courses.add(new Course("PHYS101", "Physics I: Mechanics", "Kinematics, dynamics, and conservation laws."));
        courses.add(new Course("PHYS201", "Physics II: Electricity", "Electricity, magnetism, and circuits."));
        courses.add(new Course("PHYS301", "Quantum Mechanics", "Introduction to quantum theory."));
        
        // Business Courses
        courses.add(new Course("BUS101", "Introduction to Business", "Overview of business principles and practices."));
        courses.add(new Course("BUS201", "Marketing", "Marketing principles and consumer behavior."));
        courses.add(new Course("BUS301", "Finance", "Financial management and analysis."));
        courses.add(new Course("BUS401", "Business Strategy", "Strategic planning and competitive analysis."));
        
        // Arts Courses
        courses.add(new Course("ART101", "Introduction to Art", "Survey of art history and principles."));
        courses.add(new Course("ART201", "Drawing", "Techniques and practices in drawing."));
        courses.add(new Course("ART301", "Painting", "Exploration of painting techniques."));
        
        // Language Courses
        courses.add(new Course("LANG101", "Spanish I", "Introduction to Spanish language."));
        courses.add(new Course("LANG102", "Spanish II", "Intermediate Spanish conversation and grammar."));
        courses.add(new Course("LANG201", "French I", "Introduction to French language."));
        courses.add(new Course("LANG202", "French II", "Intermediate French conversation and grammar."));
        
        // Engineering Courses
        courses.add(new Course("ENG101", "Engineering Principles", "Introduction to engineering concepts."));
        courses.add(new Course("ENG201", "Circuit Analysis", "Analysis of electrical circuits."));
        courses.add(new Course("ENG301", "Thermodynamics", "Principles of energy and heat transfer."));
        
        return courses;
    }
    
    /**
     * Creates a list of students with different names and emails.
     *
     * @return list of students
     */
    private List<Student> createStudents() {
        List<Student> students = new ArrayList<>();
        
        // First batch of students
        students.add(new Student("John", "Doe", "john.doe@example.com"));
        students.add(new Student("Jane", "Smith", "jane.smith@example.com"));
        students.add(new Student("Robert", "Johnson", "robert.johnson@example.com"));
        students.add(new Student("Emily", "Williams", "emily.williams@example.com"));
        students.add(new Student("Michael", "Brown", "michael.brown@example.com"));
        students.add(new Student("Sarah", "Jones", "sarah.jones@example.com"));
        students.add(new Student("David", "Miller", "david.miller@example.com"));
        students.add(new Student("Jessica", "Davis", "jessica.davis@example.com"));
        students.add(new Student("Daniel", "Garcia", "daniel.garcia@example.com"));
        students.add(new Student("Lisa", "Rodriguez", "lisa.rodriguez@example.com"));
        
        // Second batch of students
        students.add(new Student("James", "Wilson", "james.wilson@example.com"));
        students.add(new Student("Jennifer", "Martinez", "jennifer.martinez@example.com"));
        students.add(new Student("Christopher", "Anderson", "christopher.anderson@example.com"));
        students.add(new Student("Elizabeth", "Taylor", "elizabeth.taylor@example.com"));
        students.add(new Student("Matthew", "Thomas", "matthew.thomas@example.com"));
        students.add(new Student("Ashley", "Hernandez", "ashley.hernandez@example.com"));
        students.add(new Student("Anthony", "Moore", "anthony.moore@example.com"));
        students.add(new Student("Amanda", "Martin", "amanda.martin@example.com"));
        students.add(new Student("Joshua", "Jackson", "joshua.jackson@example.com"));
        students.add(new Student("Megan", "Thompson", "megan.thompson@example.com"));
        
        // Third batch of students
        students.add(new Student("Andrew", "White", "andrew.white@example.com"));
        students.add(new Student("Samantha", "Lopez", "samantha.lopez@example.com"));
        students.add(new Student("Ryan", "Lee", "ryan.lee@example.com"));
        students.add(new Student("Stephanie", "Gonzalez", "stephanie.gonzalez@example.com"));
        students.add(new Student("Kevin", "Harris", "kevin.harris@example.com"));
        students.add(new Student("Lauren", "Clark", "lauren.clark@example.com"));
        students.add(new Student("Brian", "Lewis", "brian.lewis@example.com"));
        students.add(new Student("Rebecca", "Robinson", "rebecca.robinson@example.com"));
        students.add(new Student("Brandon", "Walker", "brandon.walker@example.com"));
        students.add(new Student("Nicole", "Perez", "nicole.perez@example.com"));
        
        // Fourth batch of students
        students.add(new Student("Justin", "Hall", "justin.hall@example.com"));
        students.add(new Student("Brittany", "Young", "brittany.young@example.com"));
        students.add(new Student("Tyler", "Allen", "tyler.allen@example.com"));
        students.add(new Student("Hannah", "Sanchez", "hannah.sanchez@example.com"));
        students.add(new Student("Jacob", "Wright", "jacob.wright@example.com"));
        students.add(new Student("Kayla", "King", "kayla.king@example.com"));
        students.add(new Student("Nicholas", "Scott", "nicholas.scott@example.com"));
        students.add(new Student("Victoria", "Green", "victoria.green@example.com"));
        students.add(new Student("Jason", "Baker", "jason.baker@example.com"));
        students.add(new Student("Olivia", "Adams", "olivia.adams@example.com"));
        
        // Fifth batch of students
        students.add(new Student("Jonathan", "Nelson", "jonathan.nelson@example.com"));
        students.add(new Student("Rachel", "Hill", "rachel.hill@example.com"));
        students.add(new Student("Joseph", "Ramirez", "joseph.ramirez@example.com"));
        students.add(new Student("Amber", "Campbell", "amber.campbell@example.com"));
        students.add(new Student("Zachary", "Mitchell", "zachary.mitchell@example.com"));
        students.add(new Student("Danielle", "Roberts", "danielle.roberts@example.com"));
        students.add(new Student("Samuel", "Carter", "samuel.carter@example.com"));
        students.add(new Student("Heather", "Phillips", "heather.phillips@example.com"));
        students.add(new Student("Adam", "Evans", "adam.evans@example.com"));
        students.add(new Student("Melissa", "Turner", "melissa.turner@example.com"));
        
        return students;
    }
    
    /**
     * Assigns a random number of courses to each student.
     *
     * @param students list of students
     * @param courses list of courses
     * @param studentRepository the student repository
     */
    private void assignCoursesToStudents(List<Student> students, List<Course> courses, StudentRepository studentRepository) {
        for (Student student : students) {
            // Assign 3-7 random courses to each student
            int numCourses = 3 + random.nextInt(5);
            List<Course> shuffledCourses = new ArrayList<>(courses);
            java.util.Collections.shuffle(shuffledCourses);
            
            for (int i = 0; i < numCourses && i < shuffledCourses.size(); i++) {
                student.addCourse(shuffledCourses.get(i));
            }
            
            studentRepository.save(student);
        }
    }

    @Override
    public void run(String... args) throws Exception {
        if (!loadSampleData) {
            logger.info("Sample data loading is disabled");
            return;
        }
    }
} 