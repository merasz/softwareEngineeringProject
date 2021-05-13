#!/bin/bash


host_var=$(hostname -I)

echo "{
  \"ipAddress\" : \"192.168.0.10\",
  \"apiKey\" : \"7173b055-4674-4ca2-8348-60e1b3fa8204\",
  \"backend\" : \"${host_var::-1}\"
}
" > config.json
