import Login from "./containers/Login";
import Signup from "./containers/Signup";

<Route path="/" component={App}>
<Route path="/signup">
  <Signup />
</Route>

<Route path="/login">
  <Login />
</Route>
     
</Route>