<template>
  <div id="app">
    <!--Navbar-->
    <mdb-navbar position="top" class="indigo" dark>
      <!-- Navbar brand -->
      <mdb-navbar-brand href="https://mdbootstrap.com/">
        Navbar
      </mdb-navbar-brand>
      <mdb-navbar-toggler>
        <mdb-navbar-nav>
          <mdb-nav-item href="/" active>Home</mdb-nav-item>
          <mdb-nav-item href="/scrambles">Scrambles</mdb-nav-item>
          <mdb-nav-item href="#">Pricing</mdb-nav-item>
          <!-- Dropdown -->
          <mdb-dropdown tag="li" class="nav-item">
            <mdb-dropdown-toggle tag="a" navLink color="indigo" slot="toggle" waves-fixed>Dropdown</mdb-dropdown-toggle>
            <mdb-dropdown-menu>
              <mdb-dropdown-item>Action</mdb-dropdown-item>
              <mdb-dropdown-item>Another action</mdb-dropdown-item>
              <mdb-dropdown-item>Something else here</mdb-dropdown-item>
            </mdb-dropdown-menu>
          </mdb-dropdown>
        </mdb-navbar-nav>
        <!-- Search form -->
        <form class="form-inline">
          <input class="form-control mr-auto p-2" type="text" placeholder="Search" aria-label="Search">
        </form>
      </mdb-navbar-toggler>
    </mdb-navbar>


    
    <img src="./assets/logo-mdb-vue-small.png">
    <router-view/>
  </div>
</template>

<script>
import { mdbNavbar, mdbNavItem, mdbNavbarNav, mdbNavbarToggler, mdbContainer, mdbDropdown, mdbDropdownItem, mdbDropdownMenu, mdbDropdownToggle, mdbInput, mdbNavbarBrand, mdbIcon, mdbRow } from 'mdbvue';

export default {
  name: 'App',
    components: {
    mdbNavbar,
    mdbNavItem,
    mdbNavbarNav,
    mdbNavbarToggler,
    mdbContainer,
    mdbDropdown,
    mdbDropdownItem,
    mdbDropdownMenu,
    mdbDropdownToggle,
    mdbInput,
    mdbNavbarBrand,
    mdbIcon,
    mdbRow
  }

}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 100px;
}
</style>