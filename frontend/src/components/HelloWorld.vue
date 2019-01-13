<template>
  <div>
    <h1>3x3 scrambles</h1>
    <div>
      <b-container fluid>
        <b-row align-h="center">
          <b-col cols="3">
            <b-form-input v-model="numberOfRotations"
                          type="number"
                          placeholder="Number of rotations"></b-form-input>
          </b-col>
          <b-col cols="1">
            <b-button size="sm" variant="primary"
                      @click="callRestService()"
            >
              Generate {{numberOfRotations}}
            </b-button>
          </b-col>
        </b-row>
      </b-container>
    </div>

    <h3>{{scramble}}</h3>

    {{debug}}
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'HelloWorld',
  data () {
    return {
      numberOfRotations: 20,
      scramble: 'not generated',
      debug: '',
      response: [],
      errors: []
    }
  },
  methods: {
    callRestService () {
      axios.get('/api/v1/scrambles/3x3',
        { params: { 'rotations': this.numberOfRotations } })
        .then(response => {
          // JSON responses are automatically parsed.
          this.response = response.data
          if (response.status === 200) {
            this.scramble = response.data
          } else {
            this.debug = response.status + ' ' + response.data
          }
        })
        .catch(e => {
          this.errors.push(e)
        })
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  h3 {
    margin: 40px 0 0;
  }

  ul {
    list-style-type: none;
    padding: 0;
  }

  li {
    display: inline-block;
    margin: 0 10px;
  }

  a {
    color: #42b983;
  }
</style>
