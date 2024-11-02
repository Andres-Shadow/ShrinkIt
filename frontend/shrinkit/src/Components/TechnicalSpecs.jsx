function TechnicalSpecs() {
  const specs = [
    {
      title: "Backend Architecture",
      description:
        "Built with a strict Hexagonal Architecture implementation, ensuring perfect separation of concerns and maintainable code structure.",
      details: [
        "Domain-Driven Design",
        "Clear Boundaries",
        "Port and Adapters Pattern",
        "Clean Code Principles",
      ],
      icon: "🎯",
    },
    {
      title: "Spring Boot 3.3",
      description:
        "Leveraging the latest Spring Boot features for robust API development and optimal performance.",
      details: [
        "RESTful APIs",
        "Dependency Injection",
        "Spring Security",
        "Exception Handling",
      ],
      icon: "🚀",
    },
    {
      title: "Data Layer",
      description:
        "Robust data persistence with JPA/Hibernate and MySQL, ensuring data integrity and efficient querying.",
      details: [
        "JPA/Hibernate",
        "MySQL",
        "Connection Pooling",
        "Transaction Management",
      ],
      icon: "💾",
    },
    {
      title: "Frontend Technology",
      description:
        "Modern React implementation with a focus on component reusability and state management.",
      details: [
        "React 18+",
        "Custom Hooks",
        "Tailwind CSS",
        "Responsive Design",
      ],
      icon: "⚛️",
    },
  ];

  return (
    <div className="py-16 px-4 bg-zinc-900">
      <div className="max-w-7xl mx-auto">
        <h2 className="text-3xl font-bold text-white text-center mb-4">
          Technical Specifications
        </h2>
        <p className="text-zinc-400 text-center mb-12 max-w-2xl mx-auto">
          Built with modern technologies and best practices, ShrinkIt!
          implements a robust architecture focusing on scalability,
          maintainability, and clean code principles.
        </p>

        <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
          {specs.map((spec, index) => (
            <div
              key={index}
              className="bg-zinc-800 p-6 rounded-lg border border-zinc-700 hover:border-zinc-600 transition-colors"
            >
              <div className="text-4xl mb-4">{spec.icon}</div>
              <h3 className="text-xl font-semibold text-white mb-3">
                {spec.title}
              </h3>
              <p className="text-zinc-400 mb-4">{spec.description}</p>
              <ul className="space-y-2">
                {spec.details.map((detail, idx) => (
                  <li
                    key={idx}
                    className="text-zinc-300 flex items-center gap-2"
                  >
                    <span className="w-1.5 h-1.5 bg-blue-400 rounded-full"></span>
                    {detail}
                  </li>
                ))}
              </ul>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}

export default TechnicalSpecs;
