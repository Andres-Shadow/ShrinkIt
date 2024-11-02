import LinkList from "./Components/LinkList";
import Header from "./Components/Header";
import TechnicalSpecs from "./Components/TechnicalSpecs";
import Footer from "./Components/Footer";

function App() {
  return (
    <div className="bg-zinc-900 min-h-screen w-full">
      <Header />
      <div className="px-4 py-8">
        <div className="bg-zinc-800 rounded-xl p-8 shadow-lg max-w-7xl mx-auto">
          <LinkList />
        </div>
      </div>
      <TechnicalSpecs />
      <Footer />
    </div>
  );
}

export default App;
