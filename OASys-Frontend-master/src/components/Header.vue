<template>
    <div class="header">
        <ul class="nav-list">
            <li v-if="auth" class="nav">
                welcome，{{ auth.username }}，
            </li>
<!--            <notice/>-->
            <li class="nav">
                <a href="javascript:" @click="logout">
                    exit
                </a>
            </li>
        </ul>
    </div>
</template>

<script>
    import {mapState} from "vuex";
    import {logout} from "../utils/api";
    import {removeAuth} from "../utils/auth";
    // import Notice from "./Notice";

    export default {
        name: "Header",
        // components: {Notice},
        computed: mapState([
            "auth"
        ]),
        methods: {
            logout() {
                this.$confirm("sign out？", "notice", {type: "warning",})
                    .then(() => {
                        logout().then(response => {
                            if (response && response.status === "success") {
                                removeAuth()
                                this.$router.push({name: "login"})
                                this.$message.success(response.message)
                            }
                        })
                    })
                    .catch(() => {
                        this.$message.warning("calceld！")
                    })
            }
        },
    }
</script>

<style scoped>
    .header {
        display: flex;
        margin: auto;
    }

    .nav-list {
        margin-left: auto;
        display: flex;
    }

    .nav {
        margin-left: 10px;
    }

    a {
        color: #707070;
    }
</style>