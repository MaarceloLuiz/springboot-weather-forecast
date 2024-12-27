#!/bin/bash

if [ -z "$GITHUB_TOKEN" ]; then
  echo "Error: GITHUB_TOKEN is not set in the environment."
  exit 1
fi

cat <<EOF > ~/.m2/settings.xml
<settings>
  <servers>
    <server>
      <id>github</id>
      <username>GITHUB</username>
      <password>${GITHUB_TOKEN}</password>
    </server>
  </servers>
</settings>
EOF

echo "Maven settings configured successfully."
