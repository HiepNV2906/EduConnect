const express = require('express')
const { connection } = require('../config/db')

const router = express.Router()

// routes toi user
router.get('/', (req, res) => {
    res.render('user/login')
})
// routes toi user/register
router.get('/register', (req, res) => {
    res.render('user/register')
})

// routes toi user/control
router.get('/control', (req, res) => {
    res.render('user/control')
})
// routes toi user/history
router.get('/history', (req, res) => {
    res.render('user/history')
})

module.exports = router