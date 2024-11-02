import LinkList from "./Components/LinkList";
import Header from "./Components/Header";
import TechnicalSpecs from "./Components/TechnicalSpecs";
import Footer from "./Components/Footer";

function App() {
  return (
    <>
      <div className="bg-zinc-800 min-h-screen w-full">
        <Header />
        <LinkList />
        <TechnicalSpecs />
        <Footer />
      </div>
    </>
  );
}

export default App;
