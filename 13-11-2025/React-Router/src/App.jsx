import React from "react";
import {
  BrowserRouter,
  Routes,
  Route,
  NavLink,
  Outlet,
  useParams,
} from "react-router-dom";

// --- Simple Pages ---
function Home() {
  return <h1>Home Page</h1>;
}

function About() {
  return <div>
            <h1>About Page</h1>
            <p>This is a simple React application demonstrating the use of React Router for client-side routing. <br></br>You can navigate between different pages such as Home, About, Products, and Contact without reloading the page.<br></br> The Products page includes nested routes for different product categories like Cars and Bikes. Additionally, there's a dynamic route that greets customers by their first name.</p>
         </div>;
}

function Contact() {
  return <div>
            <h1>Contact Page</h1>
            <p>You can reach us at: +91 9346819125</p>
         </div>;
}

// --- Products (Nested Routes) ---
function Products() {
  return (
    <div>
      <h1>Products Page</h1>
      <nav style={{ marginBottom: "15px" }}>
        <NavLink
          to="car"
          style={({ isActive }) => ({
            color: isActive ? "red" : "black",
            textDecoration: isActive ? "underline" : "none",
            marginRight: "10px",
          })}
        >
          Cars
        </NavLink>
        <NavLink
          to="bike"
          style={({ isActive }) => ({
            color: isActive ? "red" : "black",
            textDecoration: isActive ? "underline" : "none",
          })}
        >
          Bikes
        </NavLink>
      </nav>
      <Outlet />
    </div>
  );
}

function CarProducts() {
  return (
    <div>
      <h2>Car Products</h2>
      <ul>
        <li>Audi</li>
        <li>BMW</li>
        <li>Volvo</li>
      </ul>
    </div>
  );
}

function BikeProducts() {
  return (
    <div>
      <h2>Bike Products</h2>
      <ul>
        <li>Yamaha</li>
        <li>Suzuki</li>
        <li>Honda</li>
      </ul>
    </div>
  );
}

// --- Dynamic Route Example ---
function CustomerGreeting() {
  const { firstname } = useParams();
  return <h2>Hello, {firstname}!</h2>;
}

// --- App Component ---
export default function App() {
  return (
    <div style={{backgroundColor:"Indigo", marginRight:"350px", borderRadius:"40px", padding:"20px", color:"white"}}>
    <BrowserRouter>
      <nav style={{ marginBottom: "20px", marginRight: "25px", backgroundColor: "#f0f0f0", padding: "10px" }}>
        <NavLink
          to="/"
          end
          style={({ isActive }) => ({
            color: isActive ? "blue" : "black",
            textDecoration: isActive ? "underline" : "none",
            marginRight: "10px",
          })}
        >
          Home
        </NavLink>
        <NavLink
          to="/about"
          style={({ isActive }) => ({
            color: isActive ? "blue" : "black",
            textDecoration: isActive ? "underline" : "none",
            marginRight: "10px",
          })}
        >
          About
        </NavLink>
        <NavLink
          to="/products"
          style={({ isActive }) => ({
            color: isActive ? "blue" : "black",
            textDecoration: isActive ? "underline" : "none",
            marginRight: "10px",
          })}
        >
          Products
        </NavLink>
        <NavLink
          to="/contact"
          style={({ isActive }) => ({
            color: isActive ? "blue" : "black",
            textDecoration: isActive ? "underline" : "none",
            marginRight: "10px",
          })}
        >
          Contact
        </NavLink>
        <NavLink
          to="/customer/Prabhas"
          style={({ isActive }) => ({
            color: isActive ? "blue" : "black",
            textDecoration: isActive ? "underline" : "none",
          })}
        >
          Customer(Prabhas)
        </NavLink>
      </nav>

      <Routes>
        {/* Basic Routes */}
        <Route path="/" element={<Home />} />
        <Route path="/about" element={<About />} />
        <Route path="/contact" element={<Contact />} />

        {/* Nested Routes */}
        <Route path="/products" element={<Products />}>
          <Route index element={<h3>Please select a category</h3>} />
          <Route path="car" element={<CarProducts />} />
          <Route path="bike" element={<BikeProducts />} />
        </Route>

        {/* URL Parameter Route */}
        <Route path="/customer/:firstname" element={<CustomerGreeting />} />

        {/* Fallback */}
        <Route path="*" element={<h2>❌ 404 Page Not Found</h2>} />
      </Routes>
    </BrowserRouter>
    </div>
  );
}